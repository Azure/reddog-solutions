import logging
import azure.functions as func
from main_hf import Transformers

def main(msgIn: func.ServiceBusMessage, msgOut: func.Out[str]):
    body = msgIn.get_body().decode('utf-8')
    resp = Transformers.generate_product_names(body)
    logging.info(f'Generated product name: {body}')
    msgOut.set(resp)