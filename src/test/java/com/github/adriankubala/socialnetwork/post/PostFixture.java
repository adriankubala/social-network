package com.github.adriankubala.socialnetwork.post;

import java.sql.Date;

final class PostFixture {

    static final Long NON_EXISTING_POST_ID = -10000000L;
    static final String NON_EXISTING_POST_ID_AS_STRING = String.valueOf(NON_EXISTING_POST_ID);
    static final String POST_NOT_FOUND_EXCEPTION_MESSAGE = "Post with given id not found.";

    static final Long POST_1_ID = -1L;
    static final String POST_1_ID_AS_STRING = String.valueOf(POST_1_ID);
    static final Date POST_1_POST_DATE = Date.valueOf("2023-01-01");
    static final String POST_1_AUTHOR = "Author 1";
    static final String POST_1_CONTENT = "Content 1";
    static final Long POST_1_VIEW_COUNT = 1L;

    static final Long NEW_POST_ID = 1L;
    static final Date NEW_POST_POST_DATE = Date.valueOf("2023-02-01");
    static final String NEW_POST_AUTHOR = "New author";
    static final String NEW_POST_CONTENT = "New content";
    static final Long NEW_POST_VIEW_COUNT = 1L;
}
