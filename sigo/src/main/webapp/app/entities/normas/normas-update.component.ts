import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INormas, Normas } from 'app/shared/model/normas.model';
import { NormasService } from './normas.service';

@Component({
  selector: 'jhi-normas-update',
  templateUrl: './normas-update.component.html',
})
export class NormasUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required]],
    titulo: [null, [Validators.required]],
    date: [null, [Validators.required]],
    status: [],
  });

  constructor(protected normasService: NormasService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ normas }) => {
      this.updateForm(normas);
    });
  }

  updateForm(normas: INormas): void {
    this.editForm.patchValue({
      id: normas.id,
      codigo: normas.codigo,
      titulo: normas.titulo,
      date: normas.date,
      status: normas.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const normas = this.createFromForm();
    if (normas.id !== undefined) {
      this.subscribeToSaveResponse(this.normasService.update(normas));
    } else {
      this.subscribeToSaveResponse(this.normasService.create(normas));
    }
  }

  private createFromForm(): INormas {
    return {
      ...new Normas(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      date: this.editForm.get(['date'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INormas>>): void {
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
