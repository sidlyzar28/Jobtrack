# JobTrack — Job Application Management System

JobTrack is a full-stack job application management system that helps users track applications, monitor application progress, analyze job descriptions using AI, and compare resumes with job requirements.

## Features

- Add, edit, and delete job applications
- Track application status
- Application status history and timeline
- Search jobs by company
- Filter jobs by application status
- Dashboard with application statistics
- AI-powered Job Description Analyzer
- AI-powered Resume vs Job Description Matcher
- Persistent PostgreSQL database
- RESTful Spring Boot backend
- Responsive React frontend
- Production deployment with Vercel and Render

## Tech Stack

### Frontend
- React
- Vite
- JavaScript
- Axios
- CSS

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Spring Validation
- REST APIs
- Maven

### Database
- PostgreSQL
- Neon

### AI
- Google Gemini API

### Deployment
- Vercel — Frontend
- Render — Backend
- Docker — Backend containerization

## How It Works

1. Users add their job applications to JobTrack.
2. Applications are stored in PostgreSQL through the Spring Boot REST API.
3. Users can update application statuses such as:
   - Applied
   - Assessment
   - Interview
   - Offer
   - Rejected
4. JobTrack records status changes and displays them through an application timeline.
5. The AI Job Description Analyzer extracts structured information from a job description.
6. The Resume Matcher compares a resume with a selected job description and provides a match analysis.

###Project Structure


```
jobtrack-frontend/
├── src/
│   ├── components/
│   ├── config/
│   ├── App.jsx
│   └── main.jsx
├── package.json
└── vite.config.js




JobTrack/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/jobtrack/jobtrack/
│       │       ├── ai/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── entity/
│       │       ├── exception/
│       │       ├── repository/
│       │       └── service/
│       └── resources/
│
├── Dockerfile
├── pom.xml
└── mvnw
