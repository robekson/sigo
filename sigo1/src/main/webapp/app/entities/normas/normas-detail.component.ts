import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INormas } from 'app/shared/model/normas.model';

@Component({
  selector: 'jhi-normas-detail',
  templateUrl: './normas-detail.component.html',
})
export class NormasDetailComponent implements OnInit {
  normas: INormas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ normas }) => (this.normas = normas));
  }

  previousState(): void {
    window.history.back();
  }
}
