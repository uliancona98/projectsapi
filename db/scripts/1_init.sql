--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

-- Started on 2022-02-19 02:02:42
CREATE TABLE public.authorities (
    username character varying NOT NULL,
    authority character varying NOT NULL
);


ALTER TABLE public.authorities OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16406)
-- Name: projects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.projects (
    id integer NOT NULL,
    name character varying NOT NULL,
    expected_time double precision,
    description character varying NOT NULL,
    id_project_owner integer NOT NULL,
    state character varying NOT NULL,
    used_time double precision NOT NULL,
    start_date date
);


ALTER TABLE public.projects OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16412)
-- Name: projects_developers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.projects_developers (
    id_project integer NOT NULL,
    id_developer integer NOT NULL
);


ALTER TABLE public.projects_developers OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16411)
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.projects ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 16454)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    name character varying NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16417)
-- Name: tasks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tasks (
    id_task integer NOT NULL,
    id_project integer NOT NULL,
    description character varying,
    type character varying,
    duration double precision,
    state character varying
);


ALTER TABLE public.tasks OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16461)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    username character varying NOT NULL,
    password character varying(100) NOT NULL,
    enabled boolean
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16424)
-- Name: workers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.workers (
    id integer NOT NULL,
    name character varying NOT NULL,
    last_name character varying NOT NULL,
    second_last_name character varying NOT NULL,
    username character varying
);


ALTER TABLE public.workers OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16431)
-- Name: workers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.workers ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.workers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3355 (class 0 OID 16469)
-- Dependencies: 217
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.authorities VALUES ('uancona', 'ROLE_ADMIN');
INSERT INTO public.authorities VALUES ('sbojorquez', 'ROLE_PROJECT_OWNER');
INSERT INTO public.authorities VALUES ('ulisesa', 'ROLE_USER');
INSERT INTO public.authorities VALUES ('user', 'ROLE_USER');


--
-- TOC entry 3347 (class 0 OID 16406)
-- Dependencies: 209
-- Data for Name: projects; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.projects VALUES (1, 'Tech project5', 4.5, 'Project that mnages tech shop', 2, 'disabled', 6, '2021-12-12');
INSERT INTO public.projects VALUES (3, 'Tech project2', 4.5, 'Project that mnages tech shop', 2, 'active', 0, '2021-12-12');
INSERT INTO public.projects VALUES (4, 'Tech project3', 4.5, 'Project that mnages tech shop', 1, 'active', 0, '2021-12-12');
INSERT INTO public.projects VALUES (5, 'Tech project4', 4.5, 'Project that mnages tech shop', 1, 'active', 0, '2021-12-12');
INSERT INTO public.projects VALUES (2, 'Tech project', 4.5, 'Project that mnages tech shop2', 2, 'active', 22, '2021-12-12');


--
-- TOC entry 3349 (class 0 OID 16412)
-- Dependencies: 211
-- Data for Name: projects_developers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.projects_developers VALUES (1, 1);
INSERT INTO public.projects_developers VALUES (1, 2);
INSERT INTO public.projects_developers VALUES (1, 3);
INSERT INTO public.projects_developers VALUES (2, 2);


--
-- TOC entry 3353 (class 0 OID 16454)
-- Dependencies: 215
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles VALUES ('ROLE_ADMIN');
INSERT INTO public.roles VALUES ('ROLE_PROJECT_OWNER');
INSERT INTO public.roles VALUES ('ROLE_USER');


--
-- TOC entry 3350 (class 0 OID 16417)
-- Dependencies: 212
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tasks VALUES (1, 1, 'Project 1 Task 1', 'change', 4.5, 'active');
INSERT INTO public.tasks VALUES (2, 1, 'Task del proy1-2', 'change', 3, 'active');
INSERT INTO public.tasks VALUES (3, 1, 'Task del proy1-2', 'change', 3, 'active');
INSERT INTO public.tasks VALUES (4, 1, 'Task del proy1-2', 'change', 3, 'active');
INSERT INTO public.tasks VALUES (5, 1, 'Task del proy1-2', 'change', 3, 'active');
INSERT INTO public.tasks VALUES (6, 1, 'Task del proy1-2', 'change', 3, 'active');
INSERT INTO public.tasks VALUES (7, 1, 'Task del proy1-2', 'change', 3, 'active');
INSERT INTO public.tasks VALUES (8, 1, 'Task del proy1-2', 'change', 3, 'active');
INSERT INTO public.tasks VALUES (8, 2, 'Task del proy1-2', 'change', 7, 'active');
INSERT INTO public.tasks VALUES (9, 2, 'Task del proy1-2', 'change', 7, 'active');


--
-- TOC entry 3354 (class 0 OID 16461)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES ('sbojorquez', '$2a$12$3Gum6LK6XgYz9M4sSGN54Oc/ScbQG0pIK5.6.SxliDyss6Vh0Cu.2', true);
INSERT INTO public.users VALUES ('uancona', '$2a$12$3Gum6LK6XgYz9M4sSGN54Oc/ScbQG0pIK5.6.SxliDyss6Vh0Cu.2', true);
INSERT INTO public.users VALUES ('ulisesa', '$2a$12$3Gum6LK6XgYz9M4sSGN54Oc/ScbQG0pIK5.6.SxliDyss6Vh0Cu.2', true);
INSERT INTO public.users VALUES ('user', '$2a$12$3Gum6LK6XgYz9M4sSGN54Oc/ScbQG0pIK5.6.SxliDyss6Vh0Cu.2', true);


--
-- TOC entry 3351 (class 0 OID 16424)
-- Dependencies: 213
-- Data for Name: workers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.workers VALUES (1, 'Ulises', 'Ancona', 'Graniel', 'uancona');
INSERT INTO public.workers VALUES (2, 'Shaid', 'Bojorquez', 'Interian', 'sbojorquez');
INSERT INTO public.workers VALUES (3, 'Vladimir', 'Perez', 'Canche', 'user');


--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 210
-- Name: projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.projects_id_seq', 25, true);


--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 214
-- Name: workers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.workers_id_seq', 6, true);


--
-- TOC entry 3192 (class 2606 OID 16416)
-- Name: projects_developers projects_developers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects_developers
    ADD CONSTRAINT projects_developers_pkey PRIMARY KEY (id_project, id_developer);


--
-- TOC entry 3190 (class 2606 OID 16438)
-- Name: projects projects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- TOC entry 3198 (class 2606 OID 16460)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (name);


--
-- TOC entry 3194 (class 2606 OID 16423)
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id_task, id_project);


--
-- TOC entry 3200 (class 2606 OID 16468)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- TOC entry 3196 (class 2606 OID 16430)
-- Name: workers workers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.workers
    ADD CONSTRAINT workers_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 1259 OID 16479)
-- Name: ix_auth_username; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX ix_auth_username ON public.authorities USING btree (username, authority);


--
-- TOC entry 3207 (class 2606 OID 16480)
-- Name: authorities fk7njdse381jigfb09pdqspvg1i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT fk7njdse381jigfb09pdqspvg1i FOREIGN KEY (authority) REFERENCES public.roles(name);


--
-- TOC entry 3206 (class 2606 OID 16474)
-- Name: authorities fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES public.users(username);


--
-- TOC entry 3204 (class 2606 OID 16444)
-- Name: projects_developers id_developer_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects_developers
    ADD CONSTRAINT id_developer_fk FOREIGN KEY (id_developer) REFERENCES public.workers(id) NOT VALID;


--
-- TOC entry 3203 (class 2606 OID 16439)
-- Name: projects_developers id_project_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects_developers
    ADD CONSTRAINT id_project_fk FOREIGN KEY (id_project) REFERENCES public.projects(id) NOT VALID;


--
-- TOC entry 3202 (class 2606 OID 16432)
-- Name: projects id_project_owner_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT id_project_owner_fk FOREIGN KEY (id_project_owner) REFERENCES public.workers(id) NOT VALID;


--
-- TOC entry 3205 (class 2606 OID 16449)
-- Name: tasks id_project_task_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT id_project_task_fk FOREIGN KEY (id_project) REFERENCES public.projects(id) NOT VALID;


-- Completed on 2022-02-19 02:02:43

--
-- PostgreSQL database dump complete
--

