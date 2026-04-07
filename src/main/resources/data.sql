-- Create tables explicitly to ensure they exist before data is loaded
CREATE TABLE IF NOT EXISTS author (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(255),
    published_year INTEGER,
    author_id BIGINT,
    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS loan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE,
    CONSTRAINT fk_loan_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);

-- Authors
INSERT INTO author (name) VALUES ('George Orwell');
INSERT INTO author (name) VALUES ('Jane Austen');
INSERT INTO author (name) VALUES ('Gabriel García Márquez');
INSERT INTO author (name) VALUES ('Virginia Woolf');
INSERT INTO author (name) VALUES ('Franz Kafka');
INSERT INTO author (name) VALUES ('Ernest Hemingway');
INSERT INTO author (name) VALUES ('F. Scott Fitzgerald');
INSERT INTO author (name) VALUES ('Mark Twain');
INSERT INTO author (name) VALUES ('Charles Dickens');
INSERT INTO author (name) VALUES ('Leo Tolstoy');

-- 100 Books (10 per author)
-- George Orwell (author_id = 1)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('1984', '978-0451524935', 1949, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Animal Farm', '978-0451526342', 1945, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Burmese Days', '978-0141181707', 1934, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Down and Out in Paris and London', '978-0141182605', 1933, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Road to Wigan Pier', '978-0141182704', 1937, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Homage to Catalonia', '978-0141182605', 1938, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Coming Up for Air', '978-0141182711', 1939, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Lion and the Unicorn', '978-0141182728', 1941, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Animal Farm', '978-0451526343', 1945, 1);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('1984', '978-0451524936', 1949, 1);

-- Jane Austen (author_id = 2)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Pride and Prejudice', '978-0141439518', 1813, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Emma', '978-0141439587', 1815, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Sense and Sensibility', '978-0141439540', 1811, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Mansfield Park', '978-0141439563', 1814, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Persuasion', '978-0141439525', 1817, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Northanger Abbey', '978-0141439532', 1817, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Lady Susan', '978-0140433241', 1871, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Watsons', '978-0140433227', 1923, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Sanditon', '978-0140433210', 1871, 2);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Pride and Prejudice', '978-0141439519', 1813, 2);

-- Gabriel García Márquez (author_id = 3)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('One Hundred Years of Solitude', '978-0060883287', 1967, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Love in the Time of Cholera', '978-0140283297', 1985, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Chronicle of a Death Foretold', '978-0060883881', 1981, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Autumn of the Patriarch', '978-0060883288', 1975, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('No One Writes to the Colonel', '978-0060934552', 1961, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('In Evil Hour', '978-0060934566', 1962, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Leaf Storm', '978-0060883897', 1955, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The General in his Labyrinth', '978-0060883873', 1989, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Memories of My Melancholy Whores', '978-0060732211', 2004, 3);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Strange Pilgrims', '978-0060934573', 1992, 3);

-- Virginia Woolf (author_id = 4)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Mrs. Dalloway', '978-0156628709', 1925, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('To the Lighthouse', '978-0156907391', 1927, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Orlando', '978-0156701605', 1928, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Waves', '978-0156940916', 1931, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Room of One''s Own', '978-0156787340', 1929, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Room of One''s Own', '978-0156787340', 1929, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Jacob''s Room', '978-0156448902', 1922, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Voyage Out', '978-0140431988', 1915, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Night and Day', '978-0140431995', 1919, 4);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Death of the Moth', '978-0156286991', 1942, 4);

-- Franz Kafka (author_id = 5)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Metamorphosis', '978-0553213718', 1915, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Trial', '978-0805209990', 1925, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Castle', '978-0805209991', 1926, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Amerika', '978-0553213719', 1926, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Trial', '978-0805209992', 1925, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Metamorphosis', '978-0553213717', 1915, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Trial', '978-0805209993', 1925, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Castle', '978-0805209994', 1926, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Metamorphosis', '978-0553213720', 1915, 5);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Trial', '978-0805209995', 1925, 5);

-- Ernest Hemingway (author_id = 6)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Old Man and the Sea', '978-0684801223', 1952, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Farewell to Arms', '978-0684801710', 1929, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Sun Also Rises', '978-0743285054', 1926, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('For Whom the Bell Tolls', '978-0684803356', 1940, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Sun Also Rises', '978-0743285055', 1926, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Farewell to Arms', '978-0684801711', 1929, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Old Man and the Sea', '978-0684801224', 1952, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('For Whom the Bell Tolls', '978-0684803357', 1940, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Farewell to Arms', '978-0684801712', 1929, 6);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Sun Also Rises', '978-0743285056', 1926, 6);

-- F. Scott Fitzgerald (author_id = 7)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Great Gatsby', '978-0743273565', 1925, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Tender is the Night', '978-0743273572', 1934, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('This Side of Paradise', '978-0743273589', 1920, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Beautiful and Damned', '978-0743273596', 1922, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Great Gatsby', '978-0743273566', 1925, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Tender is the Night', '978-0743273573', 1934, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Great Gatsby', '978-0743273567', 1925, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('This Side of Paradise', '978-0743273590', 1920, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Beautiful and Damned', '978-0743273597', 1922, 7);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Great Gatsby', '978-0743273568', 1925, 7);

-- Mark Twain (author_id = 8)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Adventures of Tom Sawyer', '978-0140439990', 1876, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Adventures of Huckleberry Finn', '978-0142437163', 1884, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Connecticut Yankee', '978-0140439631', 1889, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Prince and the Pauper', '978-0140439991', 1881, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Life on the Mississippi', '978-0140439992', 1883, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Adventures of Tom Sawyer', '978-0140439993', 1876, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Adventures of Huckleberry Finn', '978-0142437164', 1884, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Connecticut Yankee', '978-0140439632', 1889, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Prince and the Pauper', '978-0140439994', 1881, 8);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Life on the Mississippi', '978-0140439995', 1883, 8);

-- Charles Dickens (author_id = 9)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Tale of Two Cities', '978-0141439600', 1859, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Great Expectations', '978-0141439617', 1861, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Oliver Twist', '978-0141439624', 1838, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('David Copperfield', '978-0140439633', 1850, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Bleak House', '978-0140439640', 1853, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('A Tale of Two Cities', '978-0141439601', 1859, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Great Expectations', '978-0141439618', 1861, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Oliver Twist', '978-0141439625', 1838, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('David Copperfield', '978-0140439634', 1850, 9);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Bleak House', '978-0140439641', 1853, 9);

-- Leo Tolstoy (author_id = 10)
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('War and Peace', '978-0143039990', 1869, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Anna Karenina', '978-0143035008', 1877, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Death of Ivan Ilyich', '978-0140447889', 1886, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Resurrection', '978-0140447896', 1899, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('War and Peace', '978-0143039991', 1869, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Anna Karenina', '978-0143035009', 1877, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('The Death of Ivan Ilyich', '978-0140447890', 1886, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Resurrection', '978-0140447897', 1899, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('War and Peace', '978-0143039992', 1869, 10);
INSERT INTO book (title, isbn, published_year, author_id) VALUES ('Anna Karenina', '978-0143035010', 1877, 10);

-- 15 Loans (mix of returned and active)
INSERT INTO loan (book_id, loan_date, return_date) VALUES (1, '2024-01-15', '2024-02-15');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (2, '2024-03-01', '2024-04-01');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (3, '2024-05-10', NULL);
INSERT INTO loan (book_id, loan_date, return_date) VALUES (4, '2024-02-20', '2024-03-20');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (5, '2024-06-01', NULL);
INSERT INTO loan (book_id, loan_date, return_date) VALUES (6, '2024-04-15', '2024-05-15');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (7, '2024-07-20', NULL);
INSERT INTO loan (book_id, loan_date, return_date) VALUES (8, '2024-03-25', '2024-04-25');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (9, '2024-08-10', NULL);
INSERT INTO loan (book_id, loan_date, return_date) VALUES (10, '2024-05-30', '2024-06-30');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (11, '2024-09-05', NULL);
INSERT INTO loan (book_id, loan_date, return_date) VALUES (12, '2024-06-15', '2024-07-15');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (13, '2024-10-01', NULL);
INSERT INTO loan (book_id, loan_date, return_date) VALUES (14, '2024-07-10', '2024-08-10');
INSERT INTO loan (book_id, loan_date, return_date) VALUES (15, '2024-11-15', NULL);