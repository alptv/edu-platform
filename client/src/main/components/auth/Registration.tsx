import {UserCredentials} from "./UserCredentials";
import {service} from "../../services/Dependencies";
import {Setter} from "../../util/Util";
import {User} from "../../services/Data";


export const Registration = (props: {
    setUser: Setter<User>
}) => {
    const submit = (login: string, password: string) => service.register(login, password)
    return (
        <div className="Login">
            <UserCredentials
                setUser={props.setUser}
                submit={submit}
                headerText="Регистрация"
                submitText="Зарегистрироваться"
                failureText="Пользователь с таким логином уже существует"/>
        </div>
    )
}

