# create az backing services, mentioned in README pre-reqs
# set the following env variables after above step, and replace <> placeholders


# builds and runs the services in parallel - requires gnu-parallel
# find . -name "pom.xml" | parallel nohup mvn spring-boot:run -f