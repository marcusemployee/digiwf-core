<div id="top"></div>

<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/it-at-m/digiwf-s3-integration">
    <img src="images/logo.png" alt="Logo" height="200">
  </a>

<h3 align="center">DigiWF S3 Integration</h3>

  <p align="center">
    This is a Spring Boot Starter library to connect to S3 compatible services
     <!-- <br />
   <a href="https://github.com/it-at-m/digiwf-s3-integration"><strong>Explore the docs »</strong></a> -->
    <br />
    <br />
     <!-- <a href="https://github.com/it-at-m/digiwf-s3-integration">View Demo</a>
    · -->
    <a href="https://github.com/it-at-m/digiwf-s3-integration/issues">Report Bug</a>
    ·
    <a href="https://github.com/it-at-m/digiwf-s3-integration/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

There are several ways to store files in S3 compatible storage. Each project often has to implement the same
functionalities and solve the same problems. With this library, we create the possibility to store and clean up files to
in a structured and simple way. Here's why:

* Files often need to be stored in folder structures
* Files often must be stored in a structured way and enriched with metadata
* Cleaning up the data must be done in a structured way
* Synchronous and asynchronous interfaces are often required

Of course, one service is not suitable for all projects, as your needs may be different. That's why we decided to
provide a Spring Boot Starter library for an integration service that can be easily customized.
Additionally a second starter library is included, which serves as a client library to handle files and folders
with the above-mentioned starter.

<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

This project is built with:

* [Spring Boot](https://spring.io/projects/spring-boot)
* [minio](https://min.io)
* [Hibernate](https://hibernate.org)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- GETTING STARTED -->

## Getting started

For an explanation on how to get started, please refer to the [quickstart doc](docs/quickstart.md).

<p align="right">(<a href="#top">back to top</a>)</p>

## Documentation

For all further documentation, please refer to the [documentations doc](docs/documentation.md).

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also
simply open an issue with the tag "enhancement". Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->

## Contact

it@m - opensource@muenchendigital.io

Join our [Slack Channel](https://join.slack.com/t/digiwf/shared_invite/zt-14jxazj1j-jq0WNtXp7S7HAwJA7tKgpw) for more
information!

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/it-at-m/digiwf-s3-integration.svg?style=for-the-badge

[contributors-url]: https://github.com/it-at-m/digiwf-s3-integration/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/it-at-m/digiwf-s3-integration.svg?style=for-the-badge

[forks-url]: https://github.com/it-at-m/digiwf-s3-integration/network/members

[stars-shield]: https://img.shields.io/github/stars/it-at-m/digiwf-s3-integration.svg?style=for-the-badge

[stars-url]: https://github.com/it-at-m/digiwf-s3-integration/stargazers

[issues-shield]: https://img.shields.io/github/issues/it-at-m/digiwf-s3-integration.svg?style=for-the-badge

[issues-url]: https://github.com/it-at-m/digiwf-s3-integration/issues

[license-shield]: https://img.shields.io/github/license/it-at-m/digiwf-s3-integration.svg?style=for-the-badge

[license-url]: https://github.com/it-at-m/digiwf-s3-integration/blob/master/LICENSE

[product-screenshot]: images/screenshot.png
