package com.malexj.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebAppUser(

    /*
     * Field: `id`
     * A unique identifier for the user or bot.
     * This number may have more than 32 significant bits
     */
    Long id,

    /*
     * Field: `is_bot`
     * Optional.
     * True, if this user is a bot. Returns in the receiver field only.
     */
    @JsonProperty("is_bot") boolean isBot,

    /*
     * Field: `first_name`
     * First name of the user or bot.
     */
    @JsonProperty("first_name") String firstName,

    /*
     * Field: `last_name`
     * Optional
     * Last name of the user or bot.
     */
    @JsonProperty("last_name") String lastName,

    /*
     * Field: `username`
     * Optional
     * Username of the user or bot.
     */
    @JsonProperty("username") String username,

    /*
     * Field: `language_code`
     * Optional
     * IETF language tag of the user's language. Returns in user field only.
     */
    @JsonProperty("language_code") String languageCode,

    /*
     * Field: `is_premium`
     * Optional.
     * True, if this user is a Telegram Premium user.
     */
    @JsonProperty("is_premium") boolean isPremium,

    /*
     * Field: `added_to_attachment_menu`
     * Optional. True, if this user added the bot to the attachment menu
     */
    @JsonProperty("added_to_attachment_menu") boolean addedToAttachmentMenu,

    /*
     * Field: `allows_write_to_pm`
     * Optional.
     * True, if this user allowed the bot to message them.
     */
    @JsonProperty("allows_write_to_pm") boolean allowsWriteToPm,

    /*
     * Field: `photo_url`
     * Optional.
     * URL of the user’s profile photo. The photo can be in .jpeg or .svg formats
     */
    @JsonProperty("photo_url") String photoUrl) {}
