--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2024-12-15 18:41:12

SET
statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

--
-- TOC entry 229 (class 1255 OID 122756)
-- Name: getrandomunknownwords(bigint, bigint, bigint); Type: FUNCTION; Schema: public; Owner: foglas
--

CREATE FUNCTION public.getrandomunknownwords(surface bigint, capacity bigint, userid bigint)
    RETURNS TABLE
            (
                id            bigint,
                text          text,
                second_form   text,
                third_form    text,
                countable     text,
                priority      integer,
                fk_userid     bigint,
                original_text text
            )
    LANGUAGE plpgsql
    AS $$

DECLARE
rows_count BIGINT;

BEGIN
SELECT COUNT(*)
INTO rows_count
FROM word;

IF
(capacity <= rows_count) THEN
RETURN QUERY
SELECT word.id,
       word.text,
       word.second_form,
       word.third_form,
       word.countable,
       word.priority,
       word.fk_userid,
       word.original_text
FROM word
WHERE word.priority >= surface
  AND word.fk_userid = userId
ORDER BY RANDOM() LIMIT capacity;
ELSE
RAISE EXCEPTION 'Not enough words';
END IF;
END;

$$;


ALTER FUNCTION public.getrandomunknownwords(surface bigint, capacity bigint, userid bigint) OWNER TO foglas;

--
-- TOC entry 230 (class 1255 OID 122757)
-- Name: getrandomwords(bigint, bigint); Type: FUNCTION; Schema: public; Owner: foglas
--

CREATE FUNCTION public.getrandomwords(capacity bigint, userid bigint)
    RETURNS TABLE
            (
                id            bigint,
                text          text,
                second_form   text,
                third_form    text,
                countable     text,
                priority      integer,
                fk_userid     bigint,
                original_text text
            )
    LANGUAGE plpgsql
    AS $$

DECLARE
total_rows bigint;
BEGIN
SELECT COUNT(*)
INTO total_rows
FROM word;

IF
(capacity <= total_rows) THEN
    RETURN QUERY
SELECT word.id,
       word.text,
       word.second_form,
       word.third_form,
       word.countable,
       word.priority,
       word.fk_userid,
       word.original_text
FROM word
WHERE word.fk_userid = userId
ORDER BY RANDOM() LIMIT capacity;
ELSE
    RAISE EXCEPTION 'there are not enough words';
END IF;
END;

$$;


ALTER FUNCTION public.getrandomwords(capacity bigint, userid bigint) OWNER TO foglas;

--
-- TOC entry 231 (class 1255 OID 122758)
-- Name: getrandomwordswithrange(bigint, bigint, bigint, bigint); Type: FUNCTION; Schema: public; Owner: foglas
--

CREATE FUNCTION public.getrandomwordswithrange(lowersurface bigint, highersurface bigint, capacity bigint,
                                               userid bigint)
    RETURNS TABLE
            (
                id            bigint,
                text          text,
                second_form   text,
                third_form    text,
                countable     text,
                priority      integer,
                fk_userid     bigint,
                original_text text
            )
    LANGUAGE plpgsql
    AS $$

DECLARE
rows_count  BIGINT;
BEGIN

SELECT COUNT(*)
INTO rows_count
FROM word;

IF
(capacity <= rows_count) THEN
RETURN QUERY
SELECT word.id,
       word.text,
       word.second_form,
       word.third_form,
       word.countable,
       word.priority,
       word.fk_userid,
       word.original_text
FROM word
WHERE (word.priority >= lowerSurface AND word.priority < higherSurface AND word.fk_userid = userId)
ORDER BY RANDOM() LIMIT capacity;
ELSE
RAISE EXCEPTION 'Not enough words';
END IF;
END;

$$;


ALTER FUNCTION public.getrandomwordswithrange(lowersurface bigint, highersurface bigint, capacity bigint, userid bigint) OWNER TO foglas;

--
-- TOC entry 232 (class 1255 OID 122759)
-- Name: getrandomwordwellknown(integer, integer, bigint); Type: FUNCTION; Schema: public; Owner: foglas
--

CREATE FUNCTION public.getrandomwordwellknown(surface integer, capacity integer, userid bigint)
    RETURNS TABLE
            (
                id            bigint,
                text          text,
                second_form   text,
                third_form    text,
                countable     text,
                priority      integer,
                fk_userid     bigint,
                original_text text
            )
    LANGUAGE plpgsql
    AS $$
DECLARE
total_rows bigint;
BEGIN
SELECT COUNT(*)
INTO total_rows
FROM word;

IF
(capacity <= total_rows) THEN
    RETURN QUERY
SELECT word.id,
       word.text,
       word.second_form,
       word.third_form,
       word.countable,
       word.priority,
       word.fk_userid,
       word.original_text
FROM word
WHERE word.priority < surface
  AND word.fk_userid = userId
ORDER BY RANDOM() LIMIT capacity;
ELSE
    RAISE EXCEPTION 'there are not enough words';
END IF;
END;
$$;


ALTER FUNCTION public.getrandomwordwellknown(surface integer, capacity integer, userid bigint) OWNER TO foglas;

SET
default_tablespace = '';

SET
default_table_access_method = heap;

--
-- TOC entry 211 (class 1259 OID 106254)
-- Name: example; Type: TABLE; Schema: public; Owner: foglas
--

CREATE TABLE public.example
(
    id        bigint NOT NULL,
    text      text,
    fk_wordid bigint
);


ALTER TABLE public.example OWNER TO foglas;

--
-- TOC entry 213 (class 1259 OID 106269)
-- Name: exampleid; Type: SEQUENCE; Schema: public; Owner: foglas
--

CREATE SEQUENCE public.exampleid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.exampleid OWNER TO foglas;

--
-- TOC entry 217 (class 1259 OID 106368)
-- Name: permission; Type: TABLE; Schema: public; Owner: foglas
--

CREATE TABLE public.permission
(
    id          bigint NOT NULL,
    name        text   NOT NULL,
    "fk_roleId" bigint NOT NULL
);


ALTER TABLE public.permission OWNER TO foglas;

--
-- TOC entry 216 (class 1259 OID 106363)
-- Name: role; Type: TABLE; Schema: public; Owner: foglas
--

CREATE TABLE public.role
(
    id          bigint NOT NULL,
    name        text   NOT NULL,
    "fk_userId" bigint NOT NULL
);


ALTER TABLE public.role OWNER TO foglas;

--
-- TOC entry 209 (class 1259 OID 106244)
-- Name: user_detail; Type: TABLE; Schema: public; Owner: foglas
--

CREATE TABLE public.user_detail
(
    nickname text,
    id       bigint NOT NULL,
    email    text,
    password text
);


ALTER TABLE public.user_detail OWNER TO foglas;

--
-- TOC entry 212 (class 1259 OID 106264)
-- Name: user_word; Type: TABLE; Schema: public; Owner: foglas
--

CREATE TABLE public.user_word
(
    id        bigint NOT NULL,
    fk_userid bigint,
    fk_wordid bigint
);


ALTER TABLE public.user_word OWNER TO foglas;

--
-- TOC entry 214 (class 1259 OID 106270)
-- Name: userid; Type: SEQUENCE; Schema: public; Owner: foglas
--

CREATE SEQUENCE public.userid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.userid OWNER TO foglas;

--
-- TOC entry 3355 (class 0 OID 0)
-- Dependencies: 214
-- Name: userid; Type: SEQUENCE OWNED BY; Schema: public; Owner: foglas
--

ALTER SEQUENCE public.userid OWNED BY public.user_detail.id;


--
-- TOC entry 210 (class 1259 OID 106249)
-- Name: word; Type: TABLE; Schema: public; Owner: foglas
--

CREATE TABLE public.word
(
    id            bigint NOT NULL,
    text          text,
    second_form   text,
    third_form    text,
    countable     text,
    fk_userid     bigint,
    priority      integer,
    original_text text
);


ALTER TABLE public.word OWNER TO foglas;

--
-- TOC entry 215 (class 1259 OID 106271)
-- Name: wordid; Type: SEQUENCE; Schema: public; Owner: foglas
--

CREATE SEQUENCE public.wordid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.wordid OWNER TO foglas;

--

--
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 213
-- Name: exampleid; Type: SEQUENCE SET; Schema: public; Owner: foglas
--

SELECT pg_catalog.setval('public.exampleid', 31, true);


--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 214
-- Name: userid; Type: SEQUENCE SET; Schema: public; Owner: foglas
--

SELECT pg_catalog.setval('public.userid', 38, true);


--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 215
-- Name: wordid; Type: SEQUENCE SET; Schema: public; Owner: foglas
--

SELECT pg_catalog.setval('public.wordid', 30, true);


--
-- TOC entry 3195 (class 2606 OID 106258)
-- Name: example Examples_pkey; Type: CONSTRAINT; Schema: public; Owner: foglas
--

ALTER TABLE ONLY public.example
    ADD CONSTRAINT "Examples_pkey" PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 106374)
-- Name: permission Permission_pkey; Type: CONSTRAINT; Schema: public; Owner: foglas
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT "Permission_pkey" PRIMARY KEY (id);


--
-- TOC entry 3199 (class 2606 OID 106367)
-- Name: role Role_pkey; Type: CONSTRAINT; Schema: public; Owner: foglas
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT "Role_pkey" PRIMARY KEY (id);


--
-- TOC entry 3191 (class 2606 OID 106248)
-- Name: user_detail UserDetails_pkey; Type: CONSTRAINT; Schema: public; Owner: foglas
--

ALTER TABLE ONLY public.user_detail
    ADD CONSTRAINT "UserDetails_pkey" PRIMARY KEY (id);


--
-- TOC entry 3197 (class 2606 OID 106268)
-- Name: user_word UserWord_pkey; Type: CONSTRAINT; Schema: public; Owner: foglas
--

ALTER TABLE ONLY public.user_word
    ADD CONSTRAINT "UserWord_pkey" PRIMARY KEY (id);


--
-- TOC entry 3193 (class 2606 OID 106253)
-- Name: word Word_pkey; Type: CONSTRAINT; Schema: public; Owner: foglas
--

ALTER TABLE ONLY public.word
    ADD CONSTRAINT "Word_pkey" PRIMARY KEY (id);


-- Completed on 2024-12-15 18:41:12

--
-- PostgreSQL database dump complete
--

