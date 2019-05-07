
drop table if exists wdview.twitter_news;
CREATE TABLE wdview.twitter_news(
    ID INT PRIMARY KEY NOT NULL,
    ACCOUNT_ID INT,
    ABOUT TEXT,
    COMMENT TEXT,
    FOLLOWER TEXT,
    FOLLOWING TEXT,
    LIKE TEXT,
    TWEET TEXT
);

drop table if exists wdview.twitter_account;
CREATE TABLE wdview.twitter_account(
    ID INT PRIMARY KEY NOT NULL,
    NAME TEXT
);

drop table if exists wdview.web;
CREATE TABLE wdview.web(
    ID INT PRIMARY KEY NOT NULL,
    DATA TEXT
);


