//
//  FloatingActionButton.swift
//  Duetto
//
//  Created by Lily Suau on 1/18/18.
//  Copyright © 2018 Lily Suau. All rights reserved.
//

import UIKit

class FloatingActionButton: UIButtonX {
    
    override func beginTracking(_ touch: UITouch, with event: UIEvent?) -> Bool {
        
        UIView.animate(withDuration: 0.3, animations: {
            
            if self.transform == .identity {
                self.transform = CGAffineTransform(rotationAngle: 45*(.pi/180))
            } else {
                self.transform = .identity
            }
        })
        return super.beginTracking(touch, with: event)
    }
    
    override func endTracking(_ touch: UITouch?, with event: UIEvent?) {
        UIView.animate(withDuration: 0.35, delay: 0, options: .allowUserInteraction, animations: {
            self.alpha = self.alphaBefore
        })
    }
    
}

