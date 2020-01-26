CREATE TABLE IF NOT EXISTS students
(
    id         UUID PRIMARY KEY NOT NULL,
    first_name VARCHAR(100)     NOT NULL,
    last_name  VARCHAR(100)     NOT NULL,
    email      VARCHAR(100)     NOT NULL UNIQUE,
    gender     VARCHAR(6)       NOT NULL
        CHECK (
                gender = 'MALE' OR
                gender = 'male' OR
                gender = 'FEMALE' OR
                gender = 'female'
            )
);

CREATE TABLE IF NOT EXISTS courses
(
    id          UUID         NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    instructor  VARCHAR(100) NOT NULL,
    department  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS registrations
(
    id         UUID NOT NULL PRIMARY KEY,
    student_id UUID NOT NULL REFERENCES students (id),
    course_id  UUID NOT NULL REFERENCES courses (id),
    start_date DATE NOT NULL,
    end_date   DATE NOT NULL,
    grade      INTEGER CHECK (grade >= 0 AND grade <= 100),
    UNIQUE (student_id, course_id)
);

CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');
ALTER TABLE students
    ALTER COLUMN gender TYPE gender USING (gender::gender);
ALTER TABLE students
    DROP CONSTRAINT IF EXISTS student_gender_check;
