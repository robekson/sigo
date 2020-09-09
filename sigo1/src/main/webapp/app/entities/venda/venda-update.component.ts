import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVenda, Venda } from 'app/shared/model/venda.model';
import { VendaService } from './venda.service';
import { IFuncionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/funcionario.service';

@Component({
  selector: 'jhi-venda-update',
  templateUrl: './venda-update.component.html',
})
export class VendaUpdateComponent implements OnInit {
  isSaving = false;
  funcionarios: IFuncionario[] = [];
  dataDp: any;
  dataEntregaDp: any;

  editForm = this.fb.group({
    id: [],
    quantidade: [null, [Validators.required]],
    data: [null, [Validators.required]],
    dataEntrega: [],
    valor: [],
    funcionarioId: [],
  });

  constructor(
    protected vendaService: VendaService,
    protected funcionarioService: FuncionarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venda }) => {
      this.updateForm(venda);

      this.funcionarioService.query().subscribe((res: HttpResponse<IFuncionario[]>) => (this.funcionarios = res.body || []));
    });
  }

  updateForm(venda: IVenda): void {
    this.editForm.patchValue({
      id: venda.id,
      quantidade: venda.quantidade,
      data: venda.data,
      dataEntrega: venda.dataEntrega,
      valor: venda.valor,
      funcionarioId: venda.funcionarioId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venda = this.createFromForm();
    if (venda.id !== undefined) {
      this.subscribeToSaveResponse(this.vendaService.update(venda));
    } else {
      this.subscribeToSaveResponse(this.vendaService.create(venda));
    }
  }

  private createFromForm(): IVenda {
    return {
      ...new Venda(),
      id: this.editForm.get(['id'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value,
      data: this.editForm.get(['data'])!.value,
      dataEntrega: this.editForm.get(['dataEntrega'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      funcionarioId: this.editForm.get(['funcionarioId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenda>>): void {
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

  trackById(index: number, item: IFuncionario): any {
    return item.id;
  }
}
