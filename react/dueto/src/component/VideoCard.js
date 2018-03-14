import React, { Component } from 'react'
import { Card, Button, Typography } from 'material-ui'
import { CardMedia, CardContent, CardActions } from 'material-ui/Card'
import { getThumbnailUrl } from '../utils/fetchData.js'

class VideoCard extends Component {
  getVideoImage() {
    let image = getThumbnailUrl(this.props.artist, this.props.name)
    console.log(image)
    
    return image
  }

  render() {
    return (
        <Card style={{width: 400, margin: 40}}>
          <CardMedia>
            <img alt={this.props.name} src={this.getVideoImage()} style={{width: 400}}/>
          </CardMedia>
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
