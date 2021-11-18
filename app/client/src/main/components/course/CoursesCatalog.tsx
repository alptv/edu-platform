import "./CoursesCatalog.css"
import React from "react"
import {CourseInfoData, loadCoursesInfo} from "../../services/CourseService"
import {LoadingComponent} from "../generic/LoadingComponent"
import {List} from "../generic/List"
import {NavigationLink} from "../generic/NavigationLink"


export const CoursesCatalog = () => {
    return (
        <div className="CoursesCatalog">
            <LoadingComponent loader={loadCoursesInfo}>
                {data => (
                    <List type="o" items={data} element={CourseDescription}/>
                )}
            </LoadingComponent>
        </div>
    )
}

const CourseDescription = (courseInfoData: CourseInfoData) => (
    <div className={"CourseDescription"}>
        <h1>
            <NavigationLink to={`${courseInfoData.id}`}>{courseInfoData.name}</NavigationLink>
        </h1>
        <p>{courseInfoData.description}</p>
    </div>
)