import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoProcesso, TipoProcesso } from 'app/shared/model/tipo-processo.model';
import { TipoProcessoService } from './tipo-processo.service';
import { IProcesso } from 'app/shared/model/processo.model';
import { ProcessoService } from 'app/entities/processo/processo.service';

@Component({
  selector: 'jhi-tipo-processo-update',
  templateUrl: './tipo-processo-update.component.html',
})
export class TipoProcessoUpdateComponent implements OnInit {
  isSaving = false;
  processos: IProcesso[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    descricao: [null, [Validators.required]],
    processoId: [],
  });

  constructor(
    protected tipoProcessoService: TipoProcessoService,
    protected processoService: ProcessoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoProcesso }) => {
      this.updateForm(tipoProcesso);

      this.processoService.query().subscribe((res: HttpResponse<IProcesso[]>) => (this.processos = res.body || []));
    });
  }

  updateForm(tipoProcesso: ITipoProcesso): void {
    this.editForm.patchValue({
      id: tipoProcesso.id,
      nome: tipoProcesso.nome,
      descricao: tipoProcesso.descricao,
      processoId: tipoProcesso.processoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoProcesso = this.createFromForm();
    if (tipoProcesso.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoProcessoService.update(tipoProcesso));
    } else {
      this.subscribeToSaveResponse(this.tipoProcessoService.create(tipoProcesso));
    }
  }

  private createFromForm(): ITipoProcesso {
    return {
      ...new TipoProcesso(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      processoId: this.editForm.get(['processoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoProcesso>>): void {
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

  trackById(index: number, item: IProcesso): any {
    return item.id;
  }
}
