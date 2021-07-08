export class Feedback {
    title: string;
    contents: string;
    files: string;

    constructor(title: string, contents: string, files: string) {
        this.title = title;
        this.contents = contents;
        this.files = files;
    }
}
