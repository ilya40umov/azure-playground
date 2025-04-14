import azure.functions as func
import logging

app = func.FunctionApp()


@app.route(route="AzurePythonDemoFunc", auth_level=func.AuthLevel.ANONYMOUS)
def AzurePythonDemoFunc(req: func.HttpRequest) -> func.HttpResponse:
    logging.info("Python HTTP trigger function processed a request.")
    return func.HttpResponse("Hello, Friend!\n")
