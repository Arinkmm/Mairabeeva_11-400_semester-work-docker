# RaceTalk — Formula 1 Fan Community Website

A simple web application for Formula 1 funs built with **Java Servlets**, **FreeMarker templates**, and **PostgreSQL**.

## Features

- **User Authentication** — Secure registration and login with BCrypt password hashing
- **Session Management** — Role-based access control and authorization
- **User Profiles** — View and edit user profiles with updated personal data function
- **F1 Content** — Browse teams, drivers, and race results
- **Personal Notes** — Create, read, update, and delete your notes
- **Real-time Chat** — AJAX-powered chat with instant message delivery
- **Responsive Design** — Bootstrap 5 for user-friendly interface
- **Database Security** — JDBC with prepared statements to prevent SQL injection
- **Client-side Validation** — AJAX validation for username uniqueness and password strength on registration
- **Error Handling** — Custom error pages and exception handling

## Setup Instructions

### 1. Clone the Repository

In IntelliJ IDEA:

1. Open IntelliJ IDEA
2. On the Welcome screen, click the button **Clone Repository**
3. In the popup window:
   - **URL:** Paste `<repository-url>`
   - **Directory:** Choose where to save (or keep default)
4. Click **Clone**
5. Wait for download to complete
6. When IntelliJ asks "Would you like to open the cloned repository?", click **Yes**

The project will open automatically.

### 2. Create PostgreSQL Database

Use pgAdmin:

1. Open pgAdmin 4 (web interface for PostgreSQL)
2. Right-click **Databases** → **Create** → **Database**
3. Enter name: `racetalk_db`
4. Click **Save**
5. Open **Query Tool** (SQL editor)

Then run:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    photo VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

CREATE TABLE teams (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    country VARCHAR(50) NOT NULL,
    photo VARCHAR(255)
);

CREATE TABLE drivers (
    driver_number SERIAL PRIMARY KEY,
    team_id INT NOT NULL REFERENCES teams(id),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    country VARCHAR(50),
    photo VARCHAR(255)
);

CREATE TABLE past_races (
    id SERIAL PRIMARY KEY,
    session_key INT NOT NULL,
    location VARCHAR(100) NOT NULL,
    race_date DATE NOT NULL,
    is_finished BOOLEAN DEFAULT FALSE
);

CREATE TABLE race_results (
    id SERIAL PRIMARY KEY,
    race_id INT NOT NULL REFERENCES past_races(id) ON DELETE CASCADE,
    driver_number INT NOT NULL REFERENCES drivers(driver_number) ON DELETE CASCADE,
    position INT,
    points INT,
    UNIQUE (race_id, driver_number)
);

CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE upcoming_races (
    id SERIAL PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    race_date DATE NOT NULL,
    is_finished BOOLEAN DEFAULT FALSE
);
```

### 3. Configure Environment Variables in IntelliJ IDEA

**Step 1:** Go to **Run → Edit Configurations...**

**Step 2:** Select your Tomcat configuration (or create a new one)

**Step 3:** In the **Environment variables** field, add:

```
DATABASE_CONNECTION_URL=jdbc:postgresql://localhost:5432/racetalk_db;
DATABASE_CONNECTION_USERNAME=postgres;
DATABASE_CONNECTION_PASSWORD=<your_password>
```

Replace `<your_password>` with your actual PostgreSQL password.

**Step 4:** Click **OK** and save the configuration.

### 4. Build the Project

1. On the right side of the screen, find the **Maven** panel
2. Expand: **RaceTalk** → **Lifecycle**
3. Double-click on **clean**
4. After it finishes, double-click on **package**
5. Build logs will appear in the bottom panel

This creates a `.war` file in the `target/` folder.

### 5. Run on Tomcat

1. Go to **Run → Run 'Tomcat Server'**
2. The application will start and open in your default browser.

## Key Components

### Servlets
- `RaceServlet` — Handles race data display
- `DriverServlet` — Manages driver listings
- `TeamServlet` — Displays teams
- `NotesServlet` — CRUD operations for notes
- `ChatServlet` — Real-time chat functionality
- `LoginServlet` — User authentication
- `SignUpServlet` — User registration

### Filters
- `AuthenticationFilter` — Validates user session before accessing protected pages

### Listeners
- `InitListener` — Initializes services on application startup

### Templates
- All pages use FreeMarker for dynamic content rendering
- Consistent styling with Base CSS + page-specific CSS files