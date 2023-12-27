package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileTestData.USER_PROFILE_TO;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ProfileRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/api/profile";

    @Autowired
    ProfileRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getByGuest() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(GUEST_PROFILE_EMPTY_TO));
    }

    @Test
    public void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void update() throws Exception {
        ProfileTo updatedTo = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile profile = getUpdated(1L);
        PROFILE_MATCHER.assertMatch(repository.getOrCreate(ProfileTestData.USER_ID), profile);
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    public void create() throws Exception {
        ProfileTo newTo = getNewTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile profile = getNew(3L);
        PROFILE_MATCHER.assertMatch(repository.getOrCreate(ProfileTestData.GUEST_ID), profile);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void updateInvalidTo() throws Exception {
        ProfileTo invalidTo = getInvalidTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void updateWithUnknownNotificationTo() throws Exception {
        ProfileTo invalidTo = getWithUnknownNotificationTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void updateWithUnknownContactTo() throws Exception {
        ProfileTo invalidTo = getWithUnknownContactTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void updateWithContactHtmlUnsafeTo() throws Exception {
        ProfileTo invalidTo = getWithContactHtmlUnsafeTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}