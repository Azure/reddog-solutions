import os
import openai

class Settings():
    openai.api_type = "azure"
    openai.api_base = os.getenv("OPENAI_API_BASE")
    openai.api_version = "2022-06-01-preview"
    openai.api_key = os.getenv("OPENAI_API_KEY")


settings = Settings()