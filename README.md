# Kommunity

## Purpose

Kommunity is a simple, standalone forum application for educational purposes. It can be packaged as a fat jar and
follows the principles of 12 factor apps.

## Development setup

Prerequisites:

- Java 17 (tested with OpenJDK)
- Docker with docker-compoose
- IntelliJ (tested with Ultimate edition) or similar
    - Have the JDK named *17* in `Project Structure > Platform Settings > SDKs`

Setup instructions:

- Start backing services with docker-compose
    - IntelliJ has a run configuration called *Services*
- Start application through Gradle
    - IntelliJ has a run configuration called *KommunityApplication*, start in debug mode

This setup runs on Apple Silicon.

## License

See [LICENSE](LICENSE)