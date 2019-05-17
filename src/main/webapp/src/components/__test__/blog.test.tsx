import * as React from 'react'
import { configure, shallow, ShallowWrapper } from 'enzyme'
import { Blog } from "../Blog"
import Adapter from 'enzyme-adapter-react-16'

describe('<Blog />', () => {
    let tree: ShallowWrapper;

    beforeEach(() => {
        configure({ adapter: new Adapter() });
        tree = shallow(<Blog />);
    });

    it('renders', () => {
        expect(tree.length).toBe(1)
    })
});