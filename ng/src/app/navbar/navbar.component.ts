import {Component, OnInit} from '@angular/core';
import {AppInfoService, BuildInfo} from "../app-info.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isCollapsed = false;

  buildInfo: BuildInfo;

  constructor(private appInfoService: AppInfoService) {
  }

  ngOnInit() {
    this.appInfoService.buildInfo().subscribe(buildInfo => this.buildInfo = buildInfo);
  }

}
