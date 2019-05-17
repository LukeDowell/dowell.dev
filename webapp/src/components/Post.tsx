import * as React from 'react';
const marked = require('marked');

export interface Props {
    markdown: string
}

export class Post extends React.Component<Props, {}> {
    generateMarkdownHtml() {
        const markedOptions = {
            baseURL: process.env.PUBLIC_URL,
            sanitize: true
        };
        const renderedMarkdown = marked(this.props.markdown, markedOptions);
        return { __html: renderedMarkdown }
    }

    render() {
        return <div dangerouslySetInnerHTML={this.generateMarkdownHtml()} />
    }
}