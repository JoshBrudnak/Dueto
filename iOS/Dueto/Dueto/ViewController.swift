//
//  ViewController.swift
//  Dueto
//
//  Created by Laith Rafidi on 1/7/18.
//  Copyright © 2018 Dueto. All rights reserved.
//

import UIKit

struct cellData {
    let cell : Int!
    let text : String!
    let image : UIImage!
    
}

class TableViewController: UITableViewController {
    
    var arrayOfCellData = [cellData]()
    
    override func viewDidLoad() {
        arrayOfCellData = [cellData(cell : 1, text : "", image : #imageLiteral(resourceName: "piano")),
        cellData(cell : 1, text : "", image : #imageLiteral(resourceName: "piano")), cellData(cell : 1, text : "", image : #imageLiteral(resourceName: "piano"))]
        
        
    }
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrayOfCellData.count
    }
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
            let cell = Bundle.main.loadNibNamed("TableViewCell1", owner: self, options: nil)?.first as! TableViewCell1
            cell.mainImageView.image = arrayOfCellData[indexPath.row].image
            cell.mainLabel.text = arrayOfCellData[indexPath.row].text
            
            return cell
        
    
    }


}

