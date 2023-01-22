import base64
from io import BytesIO
from diffusers import DiffusionPipeline
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.openapi.models import Response

TESTMODEL = "thegovind/reddogpillmodel512"

pipe = DiffusionPipeline.from_pretrained(TESTMODEL)


app = FastAPI()

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get("/health")
async def pong():
    return {"status": "Ok"}


@app.get("/generate/product-image")
async def generate_product_image(prompt: str):
    print(f'Image query: {prompt}')
    return prompty(prompt)


def prompty(prompt: str):
    try:
        image = pipe(prompt).images[0]
        buffer = BytesIO()
        image.save(buffer, format="PNG")
        img = base64.b64encode(buffer.getvalue())
        return Response(content=img, media_type="image/png")

    except Exception as e:
        print(e)
        raise e
