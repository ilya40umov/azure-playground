import logging
import azure.functions as func

app = func.FunctionApp()

def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info("HTTP trigger received a request.")
    return func.HttpResponse("Hello, World!", status_code=200)
