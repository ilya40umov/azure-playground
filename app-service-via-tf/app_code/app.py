from logging.config import dictConfig

from flask import Flask, request

dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {'wsgi': {
        'class': 'logging.StreamHandler',
        'stream': 'ext://flask.logging.wsgi_errors_stream',
        'formatter': 'default'
    }},
    'root': {
        'level': 'INFO',
        'handlers': ['wsgi']
    }
})

app = Flask(__name__)


@app.route('/say_hello')
def say_hello():
    app.logger.info("Received the request.")

    if "name" in request.args.keys():
        app.logger.info(f"Saying hello a user named: {request.args.get('name')}")
        return f"Hello, {request.args.get('name')}!\n"

    app.logger.warning("Saying hello to an unknown user.")
    return "Hello, Friend!\n"
