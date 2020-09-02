import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMateriaPrima, MateriaPrima } from 'app/shared/model/materia-prima.model';
import { MateriaPrimaService } from './materia-prima.service';

@Component({
  selector: 'jhi-materia-prima-update',
  templateUrl: './materia-prima-update.component.html',
})
export class MateriaPrimaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    composicao: [],
    fio: [],
  });

  constructor(protected materiaPrimaService: MateriaPrimaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materiaPrima }) => {
      this.updateForm(materiaPrima);
    });
  }

  updateForm(materiaPrima: IMateriaPrima): void {
    this.editForm.patchValue({
      id: materiaPrima.id,
      tipo: materiaPrima.tipo,
      composicao: materiaPrima.composicao,
      fio: materiaPrima.fio,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materiaPrima = this.createFromForm();
    if (materiaPrima.id !== undefined) {
      this.subscribeToSaveResponse(this.materiaPrimaService.update(materiaPrima));
    } else {
      this.subscribeToSaveResponse(this.materiaPrimaService.create(materiaPrima));
    }
  }

  private createFromForm(): IMateriaPrima {
    return {
      ...new MateriaPrima(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      composicao: this.editForm.get(['composicao'])!.value,
      fio: this.editForm.get(['fio'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMateriaPrima>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
