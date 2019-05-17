import * as React from 'react'
import { configure, shallow, ShallowWrapper } from 'enzyme'
import { Post } from "../Post";
import Adapter from 'enzyme-adapter-react-16'

describe('<Post />', () => {
    let tree: ShallowWrapper;
    const markdownTitle = "My Title";
    const markdownBody = "my body";
    const markdownText: string = `${markdownTitle}\n============\n*${markdownBody}*`;

    beforeEach(() => {
        configure({ adapter: new Adapter() });
        tree = shallow(<Post markdown={markdownText}/>)
    });

    it('renders', () => {
        expect(tree.length).toBe(1)
    });

    it('renders expected markdown text', () => {
        const html = tree.html();
        expect(html).toContain(markdownTitle);
        expect(html).toContain(markdownBody);
    });

    it('does not render markdown-specific syntax', () => {
        const html = tree.html();
        expect(html).not.toContain("==");
        expect(html).not.toContain("*");
    });
});