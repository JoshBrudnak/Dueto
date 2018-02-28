import React, { Component } from 'react'
import {getVideoUrl} from '../utils/fetchData.js'

class Video extends Component {
  render() {
    return (
      <video controls>
        <source src={getVideoUrl("1", "small")} type="video/mp4"/>
      </video>
    );
  }
}

export default Video;
