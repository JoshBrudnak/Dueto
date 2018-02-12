create table if not exists Artist(id serial primary key, username text, name text, age int, followerCount int, followers text, description text, location text, date text, active boolean, likeCount int);
create table if not exists Video(id serial primary key, thumbnail text, artistId int, filePath text, title text, description text, uploadTime text, views int, likes int, genre text, tags text);
create table if not exists Comment(id serial primary key, videoId int, artistId int, message text, time text);
create table if not exists Genre(id serial primary key, name text, description text);
create table if not exists Session(userId text, sessionKey text);

insert into Artist(username, name, age, followers, followerCount, description, location, date, active, likeCount) VALUES('joeshmow', 'Joe Shmow', 32, 'jimbo, john, rob, brady', 4, 'Piano player', 'Detroit, Michigan', '20140312', true, 450);
insert into Artist(username, name, age, followers, followerCount, description, location, date, active, likeCount) VALUES('johnDoe', 'John Doe', 56, 'rodney, robgrocowski', 2, 'Music Enthusiest', 'Santa Barbara, California', '20160723', true, 50);
insert into Artist(username, name, followers, followerCount, description, location, date, active, likeCount) VALUES('burtonBurton', 'Burton Burton', 'jonnyBoy', 1, 'Professional drummer and musician', 'Los Angelos, California', '20110404', true, 13008);
insert into Artist(username, name, age, followers, followerCount, description, date, active, likeCount) VALUES('lenny', 'Lenny Shmow', 21, 'rodney, robgrocowski', 2, 'Musician', '20160425', true, 80);
insert into Artist(username, name, age, followers, followerCount, description, date, active, likeCount) VALUES('bobclob', 'Bob Clobnernazki', 28, '', 0, 'Musician', '20160425', false, 10);

insert into Video(thumbnail, artistId, filePath, title, description, uploadTime, views, likes, genre, tags) VALUES('thumbnails/1/moonlightsonata.jpg', 1, 'videos/1/moonlightsonata.mp4', 'Moonlight Sonata', 'Beethovens moonlight sonata with bag pipes', '20130323', 567, 30, 'classical', 'beethoven,classic,remake');
insert into Video(thumbnail, artistId, filePath, title, description, uploadTime, views, likes, genre, tags) VALUES('thumbnails/1/salsa.jpg', 1, 'videos/1/salsa.mp4', 'Salsa Music', 'Salsa Music from the carribean',  '20160512', 468, 30, 'international', 'carribean,dance');
insert into Video(thumbnail, artistId, filePath, title, description, uploadTime, views, likes, genre, tags) VALUES('thumbnails/2/toccataEnFugue.jpg', 2, 'videos/1/toccataEnFugue.mp4', 'toccataEnFugue', 'Bachs song', '20170703', 5843, 52, 'classical', 'bach,classic,remake');
insert into Video(thumbnail, artistId, filePath, title, description, uploadTime, views, likes, genre, tags) VALUES('thumbnails/3/happyBirthday.jpg', 3, 'videos/1/happyBirthday.mp4', 'Happy Birthday 2.0', 'Modernized Happy Birthday Song', '20131031', 3456, 30, 'classical', 'classic,remake');

insert into Comment(videoId, artistId, message, time) VALUES(1, 2, 'Great work, I look forward to your next video', '20171219');
insert into Comment(videoId, artistId, message, time) VALUES(3, 1, 'I also like bag pipes', '20120318');
insert into Comment(videoId, artistId, message, time) VALUES(1, 4, 'I like trains', '20070219');
insert into Comment(videoId, artistId, message, time) VALUES(4, 1, 'I think you could use some practice', '20171219');
insert into Comment(videoId, artistId, message, time) VALUES(3, 2, 'interesting', '20161014');
insert into Comment(videoId, artistId, message, time) VALUES(2, 3, 'Thanks for the comments', '20161209');

insert into Genre(name, description) VALUES('classical', 'melodic usually orchestral instumental or vocal');
insert into Genre(name, description) VALUES('rock', 'Guitar and band music');
insert into Genre(name, description) VALUES('jazz', '');
insert into Genre(name, description) VALUES('international', 'Music from other countries');
