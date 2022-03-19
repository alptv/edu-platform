export class Course {
    constructor(
        readonly id: number,
        readonly name: string,
        readonly description: string) {
    }
}

export class Lesson {
    constructor(
        readonly id: number,
        readonly name: string) {
    }
}

export class Question {
    constructor(
        readonly id: number,
        readonly text: string,
        readonly correct_answer_option_id: number) {
    }
}

export class AnswerOption {
    constructor(
        readonly id: number,
        readonly question_id: number,
        readonly text: string) {
    }
}

export class User {
    constructor(
        readonly login: string,
        readonly password: string) {
    }
}