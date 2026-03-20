<!-- ================= HEADER ================= -->
<h1 align="center"> GitHub Access Report Service</h1>

<p align="center">
  <b>Spring Boot API to analyze GitHub organization repository access</b><br>
  Generate structured reports of users and their repository permissions
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange"/>
  <img src="https://img.shields.io/badge/SpringBoot-3.x-green"/>
  <img src="https://img.shields.io/badge/GitHub-API-black"/>
  <img src="https://img.shields.io/badge/Status-Active-success"/>
</p>

<hr>

<!-- ================= ABOUT ================= -->
<h2> About the Project</h2>

<p>
This project is a <b>Spring Boot REST API</b> that fetches data from GitHub and generates an 
<b>aggregated access report</b> showing which users have access to which repositories 
within a GitHub organization.
</p>

<ul>
  <li> Fetch repositories from organization</li>
  <li> Identify collaborators</li>
  <li> Extract access levels (admin, write, read)</li>
  <li> Generate structured JSON report</li>
</ul>

<hr>

<!-- ================= RUN ================= -->
<h2> How to Run the Project</h2>

<pre>
# Clone repository
git clone https://github.com/YOUR_USERNAME/YOUR_REPO.git

# Navigate into project
cd YOUR_REPO

# Run application
mvn spring-boot:run
</pre>

<p>Application will start at:</p>

<pre>http://localhost:8081</pre>

<hr>

<!-- ================= AUTH ================= -->
<h2> Authentication Configuration</h2>

<p>
This project uses <b>GitHub Personal Access Token (PAT)</b> for authentication.
</p>

<h3>Steps:</h3>

<ol>
  <li>Go to GitHub → Settings → Developer Settings</li>
  <li>Generate a Personal Access Token</li>
  <li>Enable permissions:
    <ul>
      <li>repo</li>
      <li>read:org</li>
    </ul>
  </li>
</ol>

<p>Add the token in <code>application.properties</code>:</p>

<pre>
github.token=YOUR_GITHUB_TOKEN
github.org=YOUR_ORG_NAME
</pre>

<hr>

<!-- ================= API ================= -->
<h2>📡 API Endpoint</h2>

<h3>Get Access Report</h3>

<pre>
GET /api/access-report
</pre>

<h3>Example Request:</h3>

<pre>
http://localhost:8081/api/access-report
</pre>

<h3>Example Response:</h3>

<pre>
{
  "user1": [
    { "repo": "Banking-App", "access": "admin" }
  ],
  "user2": [
    { "repo": "Ecommerce-App", "access": "read" }
  ]
}
</pre>

<hr>

<!-- ================= DESIGN ================= -->
<h2> Assumptions & Design Decisions</h2>

<ul>
  <li>Only organization repositories are considered</li>
  <li>Data is fetched in real-time (no caching)</li>
  <li>GitHub REST API v3 is used</li>
  <li>Access levels simplified to: admin, write, read</li>
  <li>No pagination implemented for simplicity</li>
</ul>

<hr>

<!-- ================= STRUCTURE ================= -->
<h2 Project Structure</h2>

<pre>
src/
 ├── controller/
 ├── service/
 ├── dto/
 └── config/
</pre>

<hr>

<!-- ================= FUTURE ================= -->
<h2> Future Improvements</h2>

<ul>
  <li> Add caching (Redis)</li>
  <li> Add frontend dashboard</li>
  <li> Secure API with JWT</li>
  <li> Handle GitHub rate limiting</li>
</ul>

<hr>

<!-- ================= AUTHOR ================= -->
<h2> Author</h2>

<p>
<b>Dipanshu Dhawade</b><br>
Computer Science Engineering Student
</p>

