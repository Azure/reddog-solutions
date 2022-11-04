from fastapi import Depends, FastAPI
from .config import settings
import openai

app = FastAPI()

@app.get("/health")
async def pong():
    return {"status": "Ok"}


@app.get("/generate/product-names", response_model=str)
async def generate_product_names(prompt: str):
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
    return response.choices[0].text

@app.get("/translate/to-french", response_model=str)
async def generate_product_names(prompt: str):
    response = openai.Completion.create(
        engine="generate_description",
        prompt=prompt,
        temperature=0.5,
        max_tokens=100,
        top_p=1,
        frequency_penalty=0,
        presence_penalty=0,
        stop=["\n"]
    )
    return response.choices[0].text
