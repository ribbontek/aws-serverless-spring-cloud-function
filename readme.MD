# aws-serverless-spring-cloud-functions

sudo npm install -g serverless@latest

SLS_DEBUG=* sls package

SLS_DEBUG=* serverless deploy --verbose --stage prod

SLS_DEBUG=* serverless remove --verbose --stage prod

https://www.serverless.com/examples/aws-java-spring-cloud-function-demo

https://github.com/aws/aws-lambda-java-libs/blob/main/aws-lambda-java-events/README.md

https://www.serverless.com/blog/serverless-api-gateway-domain

Install the plugin either locally or globally. Locally it will create NodeJS package files    
npm install serverless-domain-manager -g

SLS_DEBUG=* sls create_domain --verbose --stage prod

redeploy after 10 mins