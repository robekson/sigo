import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMateriaPrima, MateriaPrima } from 'app/shared/model/materia-prima.model';
import { MateriaPrimaService } from './materia-prima.service';
import { IFornece } from 'app/shared/model/fornece.model';
import { ForneceService } from 'app/entities/fornece/fornece.service';

@Component({
  selector: 'jhi-materia-prima-update',
  templateUrl: './materia-prima-update.component.html',
})
export class MateriaPrimaUpdateComponent implements OnInit {
  isSaving = false;
  forneces: IFornece[] = [];

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    composicao: [],
    fio: [],
    forneceId: [],
  });

  constructor(
    protected materiaPrimaService: MateriaPrimaService,
    protected forneceService: ForneceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materiaPrima }) => {
      this.updateForm(materiaPrima);

      this.forneceService.query().subscribe((res: HttpResponse<IFornece[]>) => (this.forneces = res.body || []));
    });
  }

  updateForm(materiaPrima: IMateriaPrima): void {
    this.editForm.patchValue({
      id: materiaPrima.id,
      tipo: materiaPrima.tipo,
      composicao: materiaPrima.composicao,
      fio: materiaPrima.fio,
      forneceId: materiaPrima.forneceId,
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
      forneceId: this.editForm.get(['forneceId'])!.value,
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

  trackById(index: number, item: IFornece): any {
    return item.id;
  }
}
