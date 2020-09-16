import { Component } from "react";

class BaseControl extends Component {
  constructor(props) {
    super(props);
  }

  createEvent(value) {
    if (this.props.onChange) {
      let event = {
        target: {
          name: this.props.name,
          value: value,
        },
      };

      this.props.onChange(event);
    }
  }
}

export default BaseControl;
