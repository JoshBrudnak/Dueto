import React, { Component } from 'react'
import { Card, Button, Typography } from 'material-ui'
import { CardMedia, CardContent, CardActions } from 'material-ui/Card'
import Musvideo from 'material-ui-icons/AccountCircle'
import { getThumbnailUrl } from '../utils/fetchData.js'

class VideoCard extends Component {
  getVideoImage() {
    let image = getThumbnailUrl(this.props.artist, this.props.name)

    if (image === undefined) {
      return <Musvideo/>
    }
    
    return image
  }

  render() {
    return (
        <Card>
          <CardMedia image={this.getVideoImage()}/>
          <CardContent>
            <Typography variant="headline" component="h2">
              {this.props.name}
            </Typography>
            <Typography>
              {this.props.desc}
            </Typography>
          </CardContent>
          <CardActions>
            <Button size="small" color="primary">
              View  
            </Button>
            <Button size="small" color="primary">
              Artist 
            </Button>
          </CardActions> 
        </Card>
    )
  }
}

export default VideoCard
