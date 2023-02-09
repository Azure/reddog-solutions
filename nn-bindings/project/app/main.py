import os

import openai
from fastapi import FastAPI, Request
from pydantic import BaseSettings


class Settings(BaseSettings):
    openai.api_type = "azure"
    openai.api_base = os.getenv("OPENAI_API_BASE")
    openai.api_version = "2022-06-01-preview"
    openai.api_key = os.getenv("OPENAI_API_KEY")


settings = Settings()
app = FastAPI()


@app.get("/health")
async def pong():
    return {"status": "Ok"}


@app.post("/generate/product-names")
async def generate_product_names_route(req: Request):
    # log prompt
    req_info = await req.json()
    print(req_info)
    product_description = req_info["product_description"]
    seed_words = req_info["seed_words"]
    if not product_description or not seed_words:
        print("No prompt provided")
        return {"error": "No prompt provided"}
    else:
        composed_prompt = f'Product description:{product_description}\nSeed words:{seed_words}'
        print(f'Prompt: {composed_prompt}')
        gpt_output = Transformers.ask_davinci_2(composed_prompt)
        return {"output": gpt_output}


class Transformers():
    @staticmethod
    def ask_davinci_2(prompt: str):
        response = openai.Completion.create(
            engine="text-davinci-002",
            prompt=prompt,
            temperature=0.8,
            max_tokens=60,
            top_p=1,
            frequency_penalty=0,
            presence_penalty=0,
            stop=None
        )
        print(f'GPT output: {response}')
        return response.choices[0].text

    @staticmethod
    def ask_custom_model(prompt: str):
        response = openai.Completion.create(
            engine="generate_description",
            prompt=prompt,
            temperature=0,
            max_tokens=64,
            top_p=1,
            frequency_penalty=0,
            presence_penalty=0,
            stop=None
        )
        return response.choices[0].text
