# Raisinchain

Raisinchain is a toy project to understand how the blockchain works.



## Directory Structure

    .
    └── server
        ├── src
        │   ├── main                                application sourses
        │   │   ├── java
        │   │   │   ├── application
        │   │   │   │   ├── configurations          Kafka configurations
        │   │   │   │   ├── controllers
        │   │   │   │   └── services
        │   │   │   ├── containers
        │   │   │   └── containersExceptions
        │   │   └── resources
        │   └── test                                test sources
        │       ├── blackbox                        integration tests
        │       │   └── controllers
        │       ├── greybox
        │       │   └── controllers
        │       ├── groovy                          unit-tests
        │       │   └── containers
        │       └── helpers
        │           └── helpers
        └── target                                  compile sources
            ├── classes
            │   ├── application
            │   │   ├── configurations
            │   │   ├── controllers
            │   │   └── services
            │   ├── containers
            │   └── containersExceptions
            ├── generated-sources
            │   └── annotations
            └── test-classes
                └── containers

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://projects.spring.io/spring-boot/) - Web Application
* [Apache Kafka](https://kafka.apache.org/) - Distributed peer-to-peer communication

 

## Authors

*  **Alexander Voroshilov** - [drsanches](https://github.com/drsanches)
*  **Marina Krylova** - [extralucile](https://github.com/extralucile)
*  **Anastasiia Shalygina** - [ShalyginaA](https://github.com/ShalyginaA)
*  **Irina Tokareva** - [IrinaTokareva](https://github.com/IrinaTokareva)
*  **Ilya Kreshkov**  - [ilushakr](https://github.com/ilushakr)

See also the list of [contributors](https://github.com/drsanches/Raisinchain/graphs/contributors) who participated in this project.



## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
