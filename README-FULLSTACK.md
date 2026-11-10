# SkillHub – full-stack mini project

This package combines the supplied Spring Boot + MySQL backend with the 10 Stitch-generated desktop pages and a JavaScript integration layer.

## Stack
- Java 21, Spring Boot 3.5, Spring Data JPA / Hibernate
- MySQL
- HTML, CSS, JavaScript
- Bootstrap 5 (used for the live integration forms, tables, alerts and responsive components)
- Original Stitch-generated styling is retained so the selected desktop designs do not break.

## Run in STS / Eclipse
1. Extract this ZIP.
2. Import `SkillHub-main/skillhub` as **Existing Maven Project**.
3. In MySQL, create the database:
   ```sql
   CREATE DATABASE skillhub;
   ```
4. Open `src/main/resources/application.properties` and set the MySQL username/password for your machine.
5. Run `SkillHubApplication.java` as **Spring Boot App**.
6. Open `http://localhost:8080/`.

## Frontend pages
- `index.html` – landing page
- `login.html` / `register.html` – real student login and registration
- `dashboard.html` – live database counts and request activity
- `explore-skills.html` – live skills and student mentor directory; send learning requests
- `my-skills.html` – publish/remove student skill offerings
- `requests.html` – sent/received requests; accept/reject incoming requests
- `profile.html` – load/update student profile
- `sessions.html` – accepted/completed learning request tracker
- `reviews.html` – display ratings and submit review for completed request

The frontend JavaScript is at:
`src/main/resources/static/assets/js/skillhub-app.js`

Bootstrap integration styles are at:
`src/main/resources/static/assets/css/skillhub-app.css`

## Backend API mapping used by the frontend
The integration uses the actual controller mappings in this backend:
- `POST /students/register`
- `POST /students/login`
- `GET/PUT /students/{id}`
- `GET /students`
- `GET/POST /skills`, `GET /skills/search?name=...`
- `GET/POST /student-skills`, `GET /student-skills/student/{id}`, `DELETE /student-skills/{id}`
- `POST /requests`, `GET /requests/learner/{id}`, `GET /requests/mentor/{id}`
- `PUT /requests/{id}/status?status=ACCEPTED|REJECTED|COMPLETED`
- `GET/POST /ratings`

No CORS configuration is needed when opening the frontend from the same Spring Boot origin (`localhost:8080`).

## Important backend scope notes
- The supplied backend does not have a separate `Session` entity or session scheduling endpoints. The Sessions page therefore displays accepted/completed learning requests as a session tracker; it does not create calendar dates, meeting links or reminders.
- There is no JWT/session-token authentication in the supplied backend. The frontend stores the returned public student profile in browser `localStorage` for navigation only. This is a mini-project integration, not production-grade authentication.
- The supplied login service compares plain-text passwords. For a real deployment, password hashing (e.g. BCrypt), authorization and protected endpoints must be added.
- A small JSON recursion fix was applied by ignoring the `Student.learningRequests` collection during serialization.
- Profile update now preserves the existing password when the profile form does not send a new password.

## Notes about Stitch and Bootstrap
The supplied Stitch files contain Tailwind-style utility classes and embedded generated CSS. Those original styles are retained to preserve the design. Bootstrap 5 is added and used for the functional integration UI. This is not a full one-for-one rewrite of every original Stitch utility class into Bootstrap classes; doing that would require a separate visual migration pass.

## Troubleshooting
- `Communications link failure`: ensure MySQL is running and database credentials are correct.
- `Access denied for user`: update `spring.datasource.username` and `spring.datasource.password`.
- Port 8080 already in use: stop the other process or change `server.port`.
- Duplicate skill name: the `skills.skill_name` column is unique; the frontend reuses an existing skill record and creates a student-skill mapping.
- Login says invalid credentials: register a student first, then log in with the same email and password.
