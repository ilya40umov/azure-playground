import azure.functions as func
import logging

logger = logging.getLogger("say_hello")
app = func.FunctionApp()


@app.route(route="say_hello", auth_level=func.AuthLevel.ANONYMOUS)
def say_hello(req: func.HttpRequest) -> func.HttpResponse:
    logger.info("Received the request.")

    if "name" in req.params.keys():
        logger.info(f"Saying hello a user named: {req.params.get('name')}")
        return func.HttpResponse(f"Hello, {req.params.get('name')}!\n")

    logger.warning("Saying hello to an unknown user.")
    return func.HttpResponse("Hello, Friend!\n")
