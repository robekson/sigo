import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMateriaPrima } from 'app/shared/model/materia-prima.model';

@Component({
  selector: 'jhi-materia-prima-detail',
  templateUrl: './materia-prima-detail.component.html',
})
export class MateriaPrimaDetailComponent implements OnInit {
  materiaPrima: IMateriaPrima | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materiaPrima }) => (this.materiaPrima = materiaPrima));
  }

  previousState(): void {
    window.history.back();
  }
}
