import sys
import os
# Üst dizinin yolunu ekle
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

import logging
from logging.handlers import RotatingFileHandler


class SingletonLogger:
    # Singleton instance
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(SingletonLogger, cls).__new__(cls)
            cls._instance._initialize_logger()
        return cls._instance

    def _initialize_logger(self):
        # Create logger
        self.logger = logging.getLogger("GlobalLogger")
        self.logger.setLevel(logging.DEBUG)  # Set log level

        # Create file handler
        file_handler = RotatingFileHandler(
            "CSE3063F24P1_GRP17/log/application.log",
            maxBytes=5 * 1024 * 1024,  # Rotate after 5MB
            backupCount=5,  # Keep last 5 log files
        )
        file_handler.setFormatter(logging.Formatter("%(asctime)s - %(levelname)s - %(message)s"))

        # Attach file handler and disable console output
        self.logger.addHandler(file_handler)
        self.logger.propagate = False

    def get_logger(self):
        return self.logger


# Usage example:
if __name__ == "__main__":
    # Get Singleton Logger
    logger = SingletonLogger().get_logger()

    # Log some messages
    logger.info("This is an info message.")
    logger.warning("This is a warning message.")
    logger.error("This is an error message.")
