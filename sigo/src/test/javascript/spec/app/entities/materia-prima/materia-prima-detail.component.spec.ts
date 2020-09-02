import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { MateriaPrimaDetailComponent } from 'app/entities/materia-prima/materia-prima-detail.component';
import { MateriaPrima } from 'app/shared/model/materia-prima.model';

describe('Component Tests', () => {
  describe('MateriaPrima Management Detail Component', () => {
    let comp: MateriaPrimaDetailComponent;
    let fixture: ComponentFixture<MateriaPrimaDetailComponent>;
    const route = ({ data: of({ materiaPrima: new MateriaPrima(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [MateriaPrimaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MateriaPrimaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MateriaPrimaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load materiaPrima on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.materiaPrima).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
