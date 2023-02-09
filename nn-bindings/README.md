# Reddog NN Bindings

This is a set of endpoints that surface Azure AI services as a REST API.

## Getting Started
To get started, you'll need to have the following installed and provisioned:
- [Docker](https://www.docker.com/)
- [Python 3.8](https://www.python.org/downloads/)
- [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli)
- [Azure Cognitive Services](https://azure.microsoft.com/en-us/services/cognitive-services/)
- [Azure Storage Account](https://azure.microsoft.com/en-us/services/storage/)
- [Azure Container Registry](https://azure.microsoft.com/en-us/services/container-registry/)
- [Azure OpenAI](https://azure.microsoft.com/en-us/services/openai/)
- [Hugging Face](https://huggingface.co/)

## Setup
To get started, you'll need to create a `.env` file in the root of the project. This file will contain all of the environment variables that are used in the project. You can use the `.env.example` file as a template.

### Azure
You'll need to create an Azure account and then create the following services:
- [Azure Cognitive Services](https://azure.microsoft.com/en-us/services/cognitive-services/)
- [Azure Storage Account](https://azure.microsoft.com/en-us/services/storage/)
- [Azure Container Registry](https://azure.microsoft.com/en-us/services/container-registry/)
- [Azure OpenAI](https://azure.microsoft.com/en-us/services/openai/)
- [Hugging Face](https://huggingface.co/)

### Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.opensource.microsoft.com.

When you submit a pull request, a CLA bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., status check, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.

:warning: :construction: Java version of [reddog-code](https://github.com/Azure/reddog-code) for [Azure Spring Apps (ASA)](https://docs.microsoft.com/en-us/azure/spring-apps/) and [App Service](https://docs.microsoft.com/en-us/azure/app-service/) is WIP