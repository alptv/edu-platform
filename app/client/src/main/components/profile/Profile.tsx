import "./Profile.css"
import {User} from "../../services/CourseService";

export const Profile = (props: {
    user?: User
}) => {
    const user = props.user!!
    return (
        <div className="Profile">
            <h1>Ваш профиль</h1>
            <div className="Info">
                <InfoField name="Логин" value={user.name}/>
            </div>
        </div>
    )
}

const InfoField = (props: {
    name: string,
    value: string

}) => (
    <div className="InfoField">
        <label className="Name">{`${props.name}:`}</label>
        <span className="Value">{props.value}</span>
    </div>
)