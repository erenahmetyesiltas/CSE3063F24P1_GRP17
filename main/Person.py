class Person:
    def __init__(self, first_name: str, last_name: str, password: str):
        self.__first_name = first_name
        self.__last_name = last_name
        self.__password = password

    def get_first_name(self) -> str:
        return self.__first_name

    def set_first_name(self, first_name: str):
        self.__first_name = first_name

    def get_last_name(self) -> str:
        return self.__last_name

    def set_last_name(self, last_name: str):
        self.__last_name = last_name

    def get_password(self) -> str:
        return self.__password

    def set_password(self, password: str):
        self.__password = password
