import * as React from 'react'
import {Post} from "./Post";

export interface Props {

}

export class Blog extends React.Component<Props, {}> {

    constructor(props: Props) {
        super(props)
    }

    render() {
        const markdownText = `dowell.dev\n==========\n### Under Construction\n![Bob The Builder](${process.env.PUBLIC_URL + '/bob.png)'}`;

        return <Post markdown={markdownText} />
    }
}