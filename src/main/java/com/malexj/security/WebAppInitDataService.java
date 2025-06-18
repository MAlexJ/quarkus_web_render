package com.malexj.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.codec.digest.HmacUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WebAppInitDataService {

  private static final String HMAC_ALGORITHM = "HmacSHA256";
  private static final String USER_KEY = "user";
  private static final String HASH_PARAM = "hash";
  private static final String JOIN_DELIMITER = "\n";
  private static final String PARAM_SEPARATOR = "&";
  private static final String KEY_VALUE_SEPARATOR = "=";

  private static final Logger LOG = Logger.getLogger(WebAppInitDataService.class);

  private final String telegramSecretKey;
  private final String telegramBotToken;

  private final ObjectMapper mapper;

  @Inject
  public WebAppInitDataService(
      @ConfigProperty(name = "webapp.telegram.secret.key") String telegramSecretKey,
      @ConfigProperty(name = "webapp.telegram.bot.token") String telegramBotToken,
      ObjectMapper objectMapper) {
    this.mapper = objectMapper;
    this.telegramSecretKey = telegramSecretKey;
    this.telegramBotToken = telegramBotToken;
  }

  public Optional<WebAppUser> validateTgInitData(String tgInitData) {
    try {
      Map.Entry<String, String> result = parseInitData(tgInitData);
      String hash = result.getKey();
      String initData = result.getValue();

      byte[] secretKey = new HmacUtils(HMAC_ALGORITHM, telegramSecretKey).hmac(telegramBotToken);
      String initDataHash = new HmacUtils(HMAC_ALGORITHM, secretKey).hmacHex(initData);

      if (!initDataHash.equals(hash)) return Optional.empty();

      return Optional.ofNullable(parseQueryString(tgInitData).get(USER_KEY))
          .flatMap(this::parseJsonToWebAppUser);
    } catch (Exception e) {
      LOG.warn("Error validating Telegram init data", e);
      return Optional.empty();
    }
  }

  private Optional<WebAppUser> parseJsonToWebAppUser(String json) {
    try {
      return Optional.of(mapper.readValue(json, WebAppUser.class));
    } catch (JsonProcessingException e) {
      LOG.warn("Failed to parse WebAppUser JSON", e);
      return Optional.empty();
    }
  }

  private Map.Entry<String, String> parseInitData(String telegramInitData) {
    Map<String, String> initData = parseQueryString(telegramInitData);
    initData = sortMap(initData);

    String hash =
        Objects.requireNonNull(initData.remove(HASH_PARAM), "the `hash` parameter is null");
    List<String> separatedData =
        initData.entrySet().stream()
            .map(pair -> pair.getKey() + KEY_VALUE_SEPARATOR + pair.getValue())
            .toList();

    return Map.entry(hash, String.join(JOIN_DELIMITER, separatedData));
  }

  private Map<String, String> parseQueryString(String queryString) {
    Map<String, String> parameters = new TreeMap<>();
    String[] pairs = queryString.split(PARAM_SEPARATOR);
    for (String pair : pairs) {
      int idx = pair.indexOf(KEY_VALUE_SEPARATOR);
      String key =
          idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) : pair;
      String value =
          idx > 0 && pair.length() > idx + 1
              ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8)
              : null;
      parameters.put(key, value);
    }
    return parameters;
  }

  private Map<String, String> sortMap(Map<String, String> map) {
    return new TreeMap<>(map);
  }
}
