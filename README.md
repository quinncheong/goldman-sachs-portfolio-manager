# Goldman Sachs Portfolio Analyzer

<div id="top"></div>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">Goldman Sachs Portfolio Analyzer</h3>

  <p align="center">
    A Fully Managed Portoflio Analyzer with secure account managemenet and permission control!
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#project-features">Project Features</a></li>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#key-libraries-used">Key Libraries Used</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#caveats-and-notes">Caveats and Notes</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#running-the-front-end">Running the Frontend</a></li>
        <li><a href="#running-the-back-end">Running the Backend</a></li>
      </ul>
    </li>
    <li><a href="#license">License</a></li>
    <li><a href="#contributors">Contributors</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

![App Overview][product-screenshot]

This Portfolio Analzyer was built as a project with Goldman Sachs to allow Financial Analysts to create, analyze, and share investment portfolios.

### Project Features

1. User Registration and Login
1. Password Validation
1. Portfolio Creator and Editor
1. Portfolio Analyzer for Standard Metrics
1. Portfolio Interactive Charts 
1. User Entitlement Management
1. Public Read-Only Portfolios
1. Access Logs
1. Secure Reset Password Flow

### Built With

* [Next.js](https://nextjs.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [MongoDB Atlas](https://www.mongodb.com)

### Key Libraries Used

1. Spring Security
2. Java Mail
3. JWT Serializer
4. Spring Data Mongo
5. Jackson
6. Lombok

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

Before you can run the application, you must have the following installed

* Java 17 and Above
* Maven and Spring Boot
* Node
* NPM

### Caveats and Notes

1. This project uses MongoDB, and will not run unless the proper Environment Variables have been loaded.
2. This project uses Spring Mail and will not run unless the SPRING MAIL Environment Variables have been configured
3. This Project consists of both a Frontend and Backend. The Frontend runs on http://localhost:3000 while the Backend runs on http://localhost:8080.

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/quinncheong/goldman-sachs-portfolio-manager.git
   ```

#### Running the Front End

1. Change Directory to the Frontend (/frontend)
2. Install NPM packages
   ```sh
   npm install
   ```
3. Duplicate a copy of `.env.local-example` in the same directory, and rename it to `.env.local`.
4. Run the Frontend
    ```sh
    npm run dev
    ```

#### Running the Back End

1. Change Directory to the backend (/backend)
2. Under backend/src/main/resources, duplicate a copy of `.env-dev-example` in the same directory and rename it to `.env-dev`. 
3. Fill in all the necessary details. 
   1. MongoDB Details can be from when creating an Atlas Account. 
   2. Spring Mail details can be found by creating a new GMAIL account to send out emails.
4. Run the follow 2 scripts to compile and run the application.
  ```sh
  ./compile.bat
  ```
  ```sh
  ./run.bat
  ```
5. If Step 4 does not work, you can run the backend directly with the command below
  ```sh
  mvn spring-boot:run
  ```

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>


## Contributors
This project was built by the following contributors:

Ang Wei Sheng - [Github](https://github.com/angweisheng)<br/>
Bryan Lee - [Github](https://github.com/bryanleezh)<br/>
Chester Chia - [Github](https://github.com/chesterchia)<br/>
Elijah Khor - [Github](https://github.com/ekcm)<br/>
Japheth Leong - [Github](https://github.com/japhethleongyh)<br/>
Quinn Cheong - [Github](https://github.com/quinncheong)<br/>

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/quinncheong/goldman-sachs-portfolio-manager.svg?style=for-the-badge
[contributors-url]: https://github.com/quinncheong/goldman-sachs-portfolio-manager/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/quinncheong/goldman-sachs-portfolio-manager.svg?style=for-the-badge
[forks-url]: https://github.com/quinncheong/goldman-sachs-portfolio-manager/network/members
[stars-shield]: https://img.shields.io/github/stars/quinncheong/goldman-sachs-portfolio-manager.svg?style=for-the-badge
[stars-url]: https://github.com/quinncheong/goldman-sachs-portfolio-manager/stargazers
[issues-shield]: https://img.shields.io/github/issues/quinncheong/goldman-sachs-portfolio-manager.svg?style=for-the-badge
[issues-url]: https://github.com/quinncheong/goldman-sachs-portfolio-manager/issues
[license-shield]: https://img.shields.io/github/license/quinncheong/goldman-sachs-portfolio-manager.svg?style=for-the-badge
[license-url]: https://github.com/quinncheong/goldman-sachs-portfolio-manager/blob/main/LICENSE.txt
[product-screenshot]: images/gpa-app.png
